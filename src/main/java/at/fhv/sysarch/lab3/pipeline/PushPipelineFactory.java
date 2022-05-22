package at.fhv.sysarch.lab3.pipeline;

import at.fhv.sysarch.lab3.animation.AnimationRenderer;
import at.fhv.sysarch.lab3.obj.Model;
import at.fhv.sysarch.lab3.pipeline.filter.*;
import com.hackoeur.jglm.Mat4;
import com.hackoeur.jglm.Matrices;
import javafx.animation.AnimationTimer;

public class PushPipelineFactory {

    public static void ConnectFilters(IFilter filter1, IFilter filter2) {
        Pipe pipe = new Pipe();
        filter1.setPipeSuccessor(pipe);
        pipe.setSuccessor(filter2);
    }

    public static AnimationTimer createPipeline(PipelineData pd) {

        ModelSource source = new ModelSource();
        ScaleFace scaleFace = new ScaleFace(pd.getViewWidth(), pd.getViewHeight());
        TransFormFace modelView = new TransFormFace();
        BackFaceCulling backFaceCulling = new BackFaceCulling();
        DepthSorting depthSorting = new DepthSorting();
        ColorFace colorFace = new ColorFace(pd.getModelColor());
        LightenFace lightenFace = new LightenFace(pd.getLightPos());
        PerspectiveProj perspectiveProj = new PerspectiveProj(pd.getProjTransform());
        ScreenSpaceTransformation screenSpaceTransformation = new ScreenSpaceTransformation(pd.getViewportTransform());
        Renderer renderer = new Renderer(pd.getGraphicsContext(), pd.getRenderingMode());

        // TODO: push from the source (model)
        ConnectFilters(source, scaleFace);

        // TODO 1. perform model-view transformation from model to VIEW SPACE coordinates
        ConnectFilters(scaleFace, modelView);


        // TODO 2. perform backface culling in VIEW SPACE
        ConnectFilters(modelView, backFaceCulling);


        // TODO 3. perform depth sorting in VIEW SPACE
        ConnectFilters(backFaceCulling, depthSorting);


        // TODO 4. add coloring (space unimportant)
        ConnectFilters(depthSorting, colorFace);


        // lighting can be switched on/off
        if (pd.isPerformLighting()) {
            // 4a. TODO perform lighting in VIEW SPACE
            ConnectFilters(colorFace, lightenFace);
            // 5. TODO perform projection transformation on VIEW SPACE coordinates
            ConnectFilters(lightenFace, perspectiveProj);
        } else {
            // 5. TODO perform projection transformation
            ConnectFilters(colorFace, perspectiveProj);
        }


        // TODO 6. perform perspective division to screen coordinates
        ConnectFilters(perspectiveProj, screenSpaceTransformation);


        // TODO 7. feed into the sink (renderer)
        ConnectFilters(screenSpaceTransformation, renderer);


        // returning an animation renderer which handles clearing of the
        // viewport and computation of the fraction
        return new AnimationRenderer(pd) {
            // TODO rotation variable goes in here
            private float rotationDegree = 0;

            /** This method is called for every frame from the JavaFX Animation
             * system (using an AnimationTimer, see AnimationRenderer). 
             * @param fraction the time which has passed since the last render call in a fraction of a second
             * @param model    the model to render 
             */
            @Override
            protected void render(float fraction, Model model) {
                // TODO compute rotation in radians
                this.rotationDegree += fraction;
                this.rotationDegree %= 360;

                // TODO create new model rotation matrix using pd.modelRotAxis
                // TODO Matrices.rotate the vector pd.getModelRotAxis() with rotation
                // TODO compute updated model-view transformation
                Mat4 rotationMatrix = Matrices.rotate(rotationDegree, pd.getModelRotAxis());

                // TODO pd.getModelTranslation() multiply with new rotation matrix
                Mat4 rotationTranslationMatrix = pd.getModelTranslation().multiply(rotationMatrix);

                // TODO multiply pd.getViewTransformation() with previous result matrix
                Mat4 rotationTranslationViewTransformMatrix = pd.getViewTransform().multiply(rotationTranslationMatrix);

                // TODO update modelView with new matrix
                modelView.setTransformationmatrix(rotationTranslationViewTransformMatrix);

                // TODO trigger rendering of the pipeline
                source.write(model);
            }
        };
    }
}