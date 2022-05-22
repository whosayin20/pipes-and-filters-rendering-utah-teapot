package at.fhv.sysarch.lab3.pipeline;

import at.fhv.sysarch.lab3.obj.Face;

import java.util.Comparator;


public class ZComparator implements Comparator<Face> {


    @Override
    public int compare(Face f1, Face f2) {
        return Integer.compare((int) avgZ(f1), (int) avgZ(f2));
    }

    private float avgZ(Face face) {
        return (face.getV1().getZ() + face.getV2().getZ() + face.getV3().getZ()) / 3;
    }


}