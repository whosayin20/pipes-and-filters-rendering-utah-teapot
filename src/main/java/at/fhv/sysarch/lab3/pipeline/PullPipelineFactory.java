package at.fhv.sysarch.lab3.pipeline;

import at.fhv.sysarch.lab3.animation.AnimationRenderer;
import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.obj.Model;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import at.fhv.sysarch.lab3.pipeline.filter.pull.*;
import at.fhv.sysarch.lab3.pipeline.filter.push.PerspectiveProj;
import com.hackoeur.jglm.Mat4;
import com.hackoeur.jglm.Matrices;
import javafx.animation.AnimationTimer;
import javafx.scene.paint.Color;

public class PullPipelineFactory {
    public static AnimationTimer createPipeline(PipelineData pd) {
        //pull from the source (model)
        PullModelSource<?, Face> source = new PullModelSource();
        PullPipe<Face> sourcePipe = new PullPipe();
        sourcePipe.setFilterPredecessor(source);

        //perform model-view transformation from model to VIEW SPACE coordinates
        PullScaleFace<Face, Face> scaleFace = new PullScaleFace<>(pd.getViewWidth(), pd.getViewHeight());
        scaleFace.setPipePredecessor(sourcePipe);
        PullPipe<Face> scalePipe = new PullPipe<>();
        scalePipe.setFilterPredecessor(scaleFace);

        PullTransFormFace<Face, Face> transFormFace = new PullTransFormFace<>();
        transFormFace.setPipePredecessor(scalePipe);
        PullPipe<Face> transformFacePipe = new PullPipe<>();
        transformFacePipe.setFilterPredecessor(transFormFace);

        //perform backface culling in VIEW SPACE
        PullBackFaceCulling<Face, Face> faceCulling = new PullBackFaceCulling<>();
        faceCulling.setPipePredecessor(transformFacePipe);
        PullPipe<Face> cullingPipe = new PullPipe<>();
        cullingPipe.setFilterPredecessor(faceCulling);

        //perform depth sorting in VIEW SPACE
        PullDepthSorting<Face, Face> depthSorting = new PullDepthSorting<>();
        depthSorting.setPipePredecessor(cullingPipe);
        PullPipe<Face> depthPipe = new PullPipe<>();
        depthPipe.setFilterPredecessor(depthSorting);

        //add coloring (space unimportant)
        PullColorFace<Face, Pair<Face, Color>> colorFace = new PullColorFace<>(pd.getModelColor());
        colorFace.setPipePredecessor(depthPipe);
        PullPipe<Pair<Face, Color>> faceColorPipe = new PullPipe<>();
        faceColorPipe.setFilterPredecessor(colorFace);

        // lighting can be switched on/off
        PullPerspectiveProj<Pair<Face, Color>, Pair<Face, Color>> perspectiveProj;
        if (pd.isPerformLighting()) {
            //perform lighting in VIEW SPACE
            PullLightenFace<Pair<Face, Color>, Pair<Face, Color>> lightenFace = new PullLightenFace<>(pd.getLightPos());
            lightenFace.setPipePredecessor(faceColorPipe);
            PullPipe<Pair<Face, Color>> lightenPipe = new PullPipe<>();
            lightenPipe.setFilterPredecessor(lightenFace);

            //perform projection transformation on VIEW SPACE coordinates
            perspectiveProj = new PullPerspectiveProj<>(pd.getProjTransform());
            perspectiveProj.setPipePredecessor(lightenPipe);

        } else {
            //perform projection transformation
            perspectiveProj = new PullPerspectiveProj<>(pd.getProjTransform());
            perspectiveProj.setPipePredecessor(faceColorPipe);

        }
        PullPipe<Pair<Face, Color>> perspectivePipe = new PullPipe<>();
        perspectivePipe.setFilterPredecessor(perspectiveProj);

        //perform perspective division to screen coordinates
        PullScreenSpaceTransformation<Pair<Face, Color>, Pair<Face, Color>> screenSpaceTransformation = new PullScreenSpaceTransformation<>(pd.getViewportTransform());
        screenSpaceTransformation.setPipePredecessor(perspectivePipe);
        PullPipe<Pair<Face, Color>> screenSpacePipe = new PullPipe<>();
        screenSpacePipe.setFilterPredecessor(screenSpaceTransformation);

        //feed into the sink (renderer)
        PullRenderer<Pair<Face, Color>, ?> pullRenderer = new PullRenderer<>(pd.getGraphicsContext(), pd.getRenderingMode());
        pullRenderer.setPipePredecessor(screenSpacePipe);

        // returning an animation renderer which handles clearing of the
        // viewport and computation of the praction
        return new AnimationRenderer(pd) {
            //rotation variable goes in here
            private float rotationDegree = 0;
            /** This method is called for every frame from the JavaFX Animation
             * system (using an AnimationTimer, see AnimationRenderer). 
             * @param fraction the time which has passed since the last render call in a fraction of a second
             * @param model    the model to render 
             */
            @Override
            protected void render(float fraction, Model model) {

                this.rotationDegree += fraction;
                this.rotationDegree %= 360;


                Mat4 rotationMatrix = Matrices.rotate(rotationDegree, pd.getModelRotAxis());


                Mat4 rotationTranslationMatrix = pd.getModelTranslation().multiply(rotationMatrix);

                Mat4 rotationTranslationViewTransformMatrix = pd.getViewTransform().multiply(rotationTranslationMatrix);

                transFormFace.setTransformMatrix(rotationTranslationViewTransformMatrix);
                source.setModel(model);
                pullRenderer.read();

            }
        };
    }
}