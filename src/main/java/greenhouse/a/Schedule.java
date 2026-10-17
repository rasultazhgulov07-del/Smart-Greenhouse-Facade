package greenhouse.a;
public class Schedule {
 public enum Status {NEW,RESERVED,CANCELLED}
 private final String id; private Status status;
 public Schedule(String id) {this(id,Status.NEW);}
 public Schedule(String id,Status status) {this.id=id;this.status=status;}
 public String id() {return id;} public Status status() {return status;}
 public void reserve() {if(status!=Status.NEW)throw new IllegalStateException("Invalid transition");status=Status.RESERVED;}
 public void cancel() {if(status!=Status.RESERVED)throw new IllegalStateException("Invalid transition");status=Status.CANCELLED;}
}
