package augusta;

import javafx.animation.AnimationTimer;
import javafx.scene.input.MouseEvent;

/**
 * Created by Efe Ozturkoglu
 * This class handles drag and drop stuff
 */
public class CodeEditor {
    public static BlockControl draggingItem;
    public static double offsetX = 0;
    public static double offsetY = 0;
    public static boolean isDragging = false;

    public static void beginDrag(BlockControl b, MouseEvent e) {
        if (isDragging) return;
        System.out.println("ahoy");
        offsetX = e.getX();
        offsetY = e.getY();
        draggingItem = b;
        isDragging = true;
        updateDrag(e);
    }
    public static void endDrag(MouseEvent e) {
        if (!isDragging) return;
        isDragging = false;
    }
    public static void updateDrag(MouseEvent e) {
        if (!isDragging || draggingItem == null) return;
        if (e.isPrimaryButtonDown()) {
            draggingItem.setLayoutX(e.getSceneX() - offsetX);
            draggingItem.setLayoutY(e.getSceneY() - offsetY);
        }
        else
            endDrag(e);
    }
}
