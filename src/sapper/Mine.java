package sapper;

public class Mine {

    private boolean detonated = false;

    public boolean isDetonated() {
        return detonated;
    }

    public void detonate() {
        detonated = true;
    }
}
