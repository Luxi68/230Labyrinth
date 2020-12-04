package entity;

public class Tuple implements Comparable<Tuple>{
    int rank;
    Profile user;

    public Tuple(int rank, Profile user){
        this.rank = rank;
        this.user = user;
    }
    @Override
    public int compareTo(Tuple tuple){
        return tuple.user.getNumberOfWins() - user.getNumberOfWins();
    }

    public int getRank() {
        return rank;
    }

    public Profile getUser() {
        return user;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public void setUser(Profile user) {
        this.user = user;
    }
}
