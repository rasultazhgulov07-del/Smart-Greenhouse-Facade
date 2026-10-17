package greenhouse.workflow;
public class Job {
 public enum Stage {PENDING_B,PENDING_C,COMPLETED,COMPENSATED}
 public final String id;public final int moisture,temp,stock;public Stage stage;
 public Job(String id,int moisture,int temp,int stock,Stage stage){this.id=id;this.moisture=moisture;this.temp=temp;this.stock=stock;this.stage=stage;}
}
