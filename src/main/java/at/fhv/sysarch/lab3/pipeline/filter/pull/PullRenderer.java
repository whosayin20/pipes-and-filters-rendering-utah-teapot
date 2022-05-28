package at.fhv.sysarch.lab3.pipeline.filter.pull;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import at.fhv.sysarch.lab3.rendering.RenderingMode;
import com.hackoeur.jglm.Vec2;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class PullRenderer <I extends Pair<Face, Color>, O> implements IPullFilter<Pair<Face, Color>, O> {

    private GraphicsContext context;
    private RenderingMode rm;
    private double halfWidth;
    private double halfHeight;
    private PullPipe<Pair<Face, Color>> pipePredecessor;

    @Override
    public void setPipePredecessor(PullPipe<Pair<Face, Color>> pipe) {
        this.pipePredecessor = pipe;
    }

    public PullRenderer(GraphicsContext context, RenderingMode rm) {
        this.context = context;
        this.rm = rm;
        this.halfWidth = context.getCanvas().getWidth() / 2;
        this.halfHeight = context.getCanvas().getHeight() / 2;
    }

    @Override
    public O read() {
        while (hasNext()){
            Pair<Face, Color> pair = pipePredecessor.read();
            Face face = pair.fst();
            Color color = pair.snd();

            Vec2 v1 = face.getV1().toScreen();
            Vec2 v2 = face.getV2().toScreen();
            Vec2 v3 = face.getV3().toScreen();

            double xpoints[] = {v1.getX() + halfWidth, v2.getX() + halfWidth, v3.getX() + halfWidth};
            double ypoints[] = {v1.getY() + halfHeight, v2.getY() + halfHeight, (v3.getY() + halfHeight)};

            if (rm == RenderingMode.POINT) {
                context.fillOval(v1.getX() + halfWidth, v1.getY() + halfHeight, 3, 3);
                context.setFill(color);
            } else if (rm == RenderingMode.WIREFRAME) {
                context.strokePolygon(xpoints, ypoints, xpoints.length);
                context.setStroke(color);
            } else if (rm == RenderingMode.FILLED) {
                context.fillPolygon(xpoints, ypoints, xpoints.length);
                context.setFill(color);
            }
        }

        return null;
    }

    @Override
    public boolean hasNext() {
        return pipePredecessor.hasNext();
    }
}
