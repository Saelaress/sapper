package sapper;

import sapper.event.MineActionEvent;
import sapper.event.MineActionListener;

import java.util.ArrayList;

public class Mine extends Item{

    @Override
    public void openItem() {
        this.opened = true;
        fireMineIsDetonated(this);
    }

    // -------------------- События --------------------
    private ArrayList<MineActionListener> mineListListener = new ArrayList<>();

    public void addMineActionListener(MineActionListener listener) {
        mineListListener.add(listener);
    }

    private void fireMineIsDetonated(Mine mine) {
        for(MineActionListener listener: mineListListener) {
            MineActionEvent event = new MineActionEvent(listener);
            event.setMine(mine);
            listener.mineIsDetonated(event);
        }
    }
}
