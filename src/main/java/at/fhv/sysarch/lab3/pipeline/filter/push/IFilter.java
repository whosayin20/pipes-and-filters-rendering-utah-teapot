package at.fhv.sysarch.lab3.pipeline.filter.push;

// TODO: Think about how generics can be applied in this context
// TODO: The current solution is JUST an illustration and not sufficient for the example. It only shows how generics may be used.
// TODO: Can you use one interface for both implementations (push and pull)? Or do they require specific interfaces?

import at.fhv.sysarch.lab3.pipeline.Pipe;

//Filter mit push/write und einmal eine read
public interface IFilter<I, O> {

    void setPipeSuccessor(Pipe<O> pipe);

    void write(I input);

}
