package sapper;

public class Sapper {

    private int life;
    private int flag;

    public Sapper(int life, int flag) {
        this.life = life;
        this.flag = flag;
    }

    public int getLife() {
        return life;
    }

    public int getFlag() {
        return flag;
    }

    public void decLife() {
        //не меньше нуля
    }

    public void demine(Cell cell) {

    }

    private void decFlag() {

    }

    private void incFlag() {

    }

    public void openCell(Cell cell) {

    }

    public void removeFlag(Cell cell) {

    }
}
