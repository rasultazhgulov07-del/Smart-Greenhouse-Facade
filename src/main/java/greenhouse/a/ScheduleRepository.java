package greenhouse.a;
import java.nio.file.*;import java.io.*;import java.util.*;
public class ScheduleRepository {
 private final Path dir;public ScheduleRepository(Path root){dir=root.resolve("a");}
 public void save(Schedule e){try{Files.createDirectories(dir);Properties p=new Properties();p.setProperty("status",e.status().name());try(var out=Files.newOutputStream(dir.resolve(e.id()+".properties"))){p.store(out,"status");}}catch(IOException x){throw new IllegalStateException(x);}}
 public Schedule find(String id){Path f=dir.resolve(id+".properties");if(!Files.exists(f))return null;try(var in=Files.newInputStream(f)){Properties p=new Properties();p.load(in);return new Schedule(id,Schedule.Status.valueOf(p.getProperty("status")));}catch(IOException x){throw new IllegalStateException(x);}}
}
