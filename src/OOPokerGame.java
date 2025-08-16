import java.util.*;

enum Suit { CLUB, DIAMOND, HEART, SPADE}
// enum constructor比較少見，解析一下
// 基本上就是把每個Rank指定value
// Java中enum constructor是implicit private，所以我們不能 new Rank(2)，
// Java自動為每一個constant呼叫了 (constant: TWO, THREE, … ACE)
// 我們可以 System.out.println(Rank.ACE.value); -> 14
//
enum Rank { TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8),
    NINE(9), TEN(10), JACK(11), QUEEN(12), KING(13), ACE(14);
    public final int value;
    Rank(int v){ this.value = v;}
    // 延伸用法: System.out.println(Rank.ACE.beats(Rank.KING)); -> true
    public int getValue() { return value; }
    public boolean beats(Rank other) { return this.value > other.value; }
}
// 每一張紙牌都有花色和數字
final class Card {
    public final Suit suit;
    public final Rank rank;
    public Card(Suit suit, Rank rank) { this.suit = suit; this.rank = rank; }
    @Override public String toString(){ return rank + " of " + suit; }
}
// 洗牌interface，利於之後用不同方法shuffle
// 甚至debug的時候可以不shuffle，可以直接按照順序發牌
// 洗牌不是factory pattern，純粹一個利於拓展的class + function
interface Shuffler {
    void shuffle(List<Card> cards);
}

// 這裡用Default Collections.shuffle
class DefaultShuffler implements Shuffler {
    private final Random rng = new Random();
    public void shuffle(List<Card> cards){ Collections.shuffle(cards, rng); }
}
// Factory pattern，in case有別的遊戲不是用52張牌
// 有些遊戲甚至會用兩副牌，8張Ace或是有鬼牌之類
// 這個標準的Factory pattern，創造的instance會根據sub-class決定
// DeckFactory factory = new StandardDeckFactory();
// List<Card> deck = factory.newDeck();
// 好處也很明顯，你要用到不同的deck的時候就用另外一個sub-class，先前寫過的code都不用動
// -> Open/Closed Principle (OCP)
interface DeckFactory {
    List<Card> newDeck();
}

// 這裡用Standard 52-card deck
class StandardDeckFactory implements DeckFactory {
    public List<Card> newDeck() {
        List<Card> deck = new ArrayList<>(52);
        for (Suit s : Suit.values())
            for (Rank r : Rank.values())
                deck.add(new Card(s, r));
        return deck;
    }
}
// Hand和Player，沒什麼特別的，定義每一手牌 還有玩家
class Hand {
    private final List<Card> cards = new ArrayList<>();
    public void add(Card c){ cards.add(c); }
    public List<Card> cards(){ return Collections.unmodifiableList(cards); }
    public int size(){ return cards.size(); }
    @Override public String toString(){ return cards.toString(); }
}

class Player {
    private final String id;
    private final Hand hand = new Hand();
    public Player(String id){ this.id = id; }
    public String id(){ return id; }
    public Hand hand(){ return hand; }
    public void clearHand(){ /* new hand each round */ }
}
// 每一手牌分勝負的方式
final class HandScore implements Comparable<HandScore> {
    // Comparable score across players for the current game’s rule set.
    // For poker: primary rank (e.g., FLUSH), then kickers, etc.
    private final List<Integer> tuple; // lexicographic comparison
    public HandScore(Integer... parts){ this.tuple = Arrays.asList(parts); }
    public int compareTo(HandScore o){
        for (int i = 0; i < Math.min(tuple.size(), o.tuple.size()); i++) {
            int cmp = Integer.compare(this.tuple.get(i), o.tuple.get(i));
            if (cmp != 0) return cmp;
        }
        return Integer.compare(this.tuple.size(), o.tuple.size());
    }
    @Override public String toString(){ return tuple.toString(); }
}

// 為了利於拓展，不同的Game用不同的rule
interface RuleSet {
    String name();
    int handSize();               // how many cards each player draws (if fixed)
    void deal(List<Card> deck, List<Player> players);  // dealing policy
    HandScore evaluate(Hand hand);                      // hand → score
    default List<Player> determineWinners(List<Player> players){
        Player best = null; HandScore bestScore = null;
        List<Player> winners = new ArrayList<>();
        for (Player p : players) {
            HandScore s = evaluate(p.hand());
            if (best == null || s.compareTo(bestScore) > 0) {
                best = p; bestScore = s; winners.clear(); winners.add(p);
            } else if (s.compareTo(bestScore) == 0) {
                winners.add(p);
            }
        }
        return winners;
    }
}

// 最簡單的一種遊戲，抽一張比大
class HighCardRuleSet implements RuleSet {
    public String name(){ return "High Card"; }
    public int handSize(){ return 1; }

    public void deal(List<Card> deck, List<Player> players){
        for (Player p : players) {
            p.hand().add(deck.remove(deck.size()-1)); // deal top
        }
    }

    public HandScore evaluate(Hand hand){
        // single card: score by rank only
        Card c = hand.cards().get(0);
        return new HandScore(c.rank.value);
    }
}

// 整合上面全部東西的遊戲，每個遊戲有自己的遊戲規則+洗牌方法+整副牌
// 可以加入玩家，可以進行一輪遊戲，可以輸出結果
abstract class CardGame {
    protected final RuleSet rules;
    protected final Shuffler shuffler;
    protected final DeckFactory deckFactory;

    protected List<Card> deck;
    protected final List<Player> players = new ArrayList<>();

    protected CardGame(RuleSet rules, DeckFactory deckFactory, Shuffler shuffler){
        this.rules = rules; this.deckFactory = deckFactory; this.shuffler = shuffler;
    }

    public void addPlayer(Player p){ players.add(p); }

    public final void playRound(){
        deck = deckFactory.newDeck();
        shuffler.shuffle(deck);
        beforeDeal(deck, players);
        rules.deal(deck, players);
        afterDeal(deck, players);

        List<Player> winners = rules.determineWinners(players);
        announceResults(winners);
        cleanup();
    }

    protected void beforeDeal(List<Card> deck, List<Player> players) { /* hook */ }
    protected void afterDeal(List<Card> deck, List<Player> players)  { /* hook */ }
    protected void announceResults(List<Player> winners){
        System.out.println("Winners:");
        for (Player p : winners) {
            System.out.println(" - " + p.id() + " with " + p.hand());
        }
    }
    protected void cleanup(){ /* reset if needed */ }
}

// 抽一張比大遊戲
class HighCardGame extends CardGame {
    public HighCardGame() {
        super(new HighCardRuleSet(), new StandardDeckFactory(), new DefaultShuffler());
    }
}

public class OOPokerGame {
    public static void main(String[] args) {
        System.out.println("OOPokerGame!!");
        // 抽一張比大遊戲
        CardGame game1 = new HighCardGame();
        game1.addPlayer(new Player("Alice"));
        game1.addPlayer(new Player("Bob"));
        game1.addPlayer(new Player("Charlie"));
        System.out.println("=== " + game1.rules.name() + " ===");
        game1.playRound();
    }
}
