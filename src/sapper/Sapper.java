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
        if(life > 0) life -= 1;
    }

    public void demine(Cell cell) {
        if(getFlag() > 0) {
            if(cell.setFlag()) decFlag();
        }
    }

    private void decFlag() {
        if(flag > 0) flag -= 1;
    }

    private void incFlag() {
        flag += 1;
    }

    public void openCell(Cell cell) {
        cell.open();
    }

    public void removeFlag(Cell cell) {
        if(cell.removeFlag()) incFlag();
    }
}
