package at.fhv.sysarch.lab3.pipeline.filter.pull;

import at.fhv.sysarch.lab3.pipeline.Pipe;

public interface IPullFilter<I, O> {

    void setPipePredecessor(PullPipe<I> pipe);

    O read();

    boolean hasNext();
}
