package greenhouse.c;
public class Nutrients {
 public enum Status {NEW,DISPENSED,CANCELLED}
 private final String id; private Status status;
 public Nutrients(String id) {this(id,Status.NEW);}
 public Nutrients(String id,Status status) {this.id=id;this.status=status;}
 public String id() {return id;} public Status status() {return status;}
 public void dispense() {if(status!=Status.NEW)throw new IllegalStateException("Invalid transition");status=Status.DISPENSED;}
 public void cancel() {if(status!=Status.DISPENSED)throw new IllegalStateException("Invalid transition");status=Status.CANCELLED;}
}
