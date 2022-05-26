package at.fhv.sysarch.lab3.pipeline.filter.pull;

public class PullPipe<O> {

    private  IPullFilter<?, O> filterPredecessor;

    public void setFilterPredecessor (IPullFilter<?, O> pullFilter) {this.filterPredecessor = pullFilter;}

    public O read(){
        return filterPredecessor.read();
    }

    public boolean hasNext(){
        return filterPredecessor.hasNext();
    }
}
