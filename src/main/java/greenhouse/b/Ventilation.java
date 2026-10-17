package greenhouse.b;
public class Ventilation {
 public enum Status {NEW,ACTIVE,STOPPED}
 private final String id; private Status status;
 public Ventilation(String id) {this(id,Status.NEW);}
 public Ventilation(String id,Status status) {this.id=id;this.status=status;}
 public String id() {return id;} public Status status() {return status;}
 public void activate() {if(status!=Status.NEW)throw new IllegalStateException("Invalid transition");status=Status.ACTIVE;}
 public void stop() {if(status!=Status.ACTIVE)throw new IllegalStateException("Invalid transition");status=Status.STOPPED;}
}
