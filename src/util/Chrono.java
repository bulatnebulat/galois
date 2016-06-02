package util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

public class Chrono {
    private String name;
    private boolean nano;
    private HashMap<String, Long> current_chronos = new HashMap();
    private HashMap<String, ArrayList<Long>> results = new HashMap();

    public Chrono(String name, boolean nano) {
        this.name = name;
        this.nano = nano;
    }

    public Chrono(String name) {
        this(name, false);
    }

    public String getName() {
        return this.name;
    }

    public void start(String serieName) {
        this.current_chronos.put(serieName, this.nano ? System.nanoTime() : System.currentTimeMillis());
    }

    public void stop(String serieName) {
        Long currentTime = this.current_chronos.get(serieName);
        if (currentTime != null) {
            ArrayList result = this.results.get(serieName);
            if (result == null) {
                result = new ArrayList();
                this.results.put(serieName, result);
            }
            result.add(this.nano ? System.nanoTime() : System.currentTimeMillis() - currentTime);
        }
    }

    public long getResult(String serieName) {
        long l = 0;
        for (Long mesure : this.results.get(serieName)) {
            l += mesure.longValue();
        }
        return l;
    }

    public ArrayList<Long> getResultArray(String serieName) {
        return this.results.get(serieName);
    }

    public long getResult() {
        long l = 0;
        for (ArrayList<Long> mesures : this.results.values()) {
            for (Long mesure : mesures) {
                l += mesure.longValue();
            }
        }
        return l;
    }

    public int getSerieCount() {
        return this.results.size();
    }

    public String[] getSerieNames() {
        return this.results.keySet().toArray(new String[this.results.keySet().size()]);
    }
}

