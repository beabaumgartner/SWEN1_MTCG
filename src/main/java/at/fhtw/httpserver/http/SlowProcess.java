package at.fhtw.httpserver.http;

public class SlowProcess {
    public long slowCounter(int dimension) {
        long counter = 0;

        for(int i = 0; i < dimension; i++) {
            for(int j = 0; j < dimension; j++) {
                counter++;
            }
        }

        return counter;
    }
}
