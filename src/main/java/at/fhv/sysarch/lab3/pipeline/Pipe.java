package at.fhv.sysarch.lab3.pipeline;

import at.fhv.sysarch.lab3.pipeline.filter.IFilter;

// TODO: how can pipes be used for different data types?
public class Pipe<I> {

    private IFilter successor;

    public void setSuccessor(IFilter<I,?> filter) {
        this.successor = filter;
    }

    public void write(I input) {
        this.successor.write(input);
    }
}
