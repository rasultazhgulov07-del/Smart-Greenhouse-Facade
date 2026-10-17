package greenhouse;
import greenhouse.a.*;import greenhouse.b.*;import greenhouse.c.*;import greenhouse.workflow.*;import greenhouse.facade.*;import java.nio.file.*;import java.util.*;
public class Main {
 public static void main(String[] args){Path root=Path.of("data");var a=new ScheduleService(new SoilRule(),new ScheduleRepository(root));var b=new VentilationService(new ClimateRule(),new VentilationRepository(root));var c=new NutrientService(new NutrientRule(),new NutrientsRepository(root));var jobs=new JobRepository(root);var facade=new GreenhouseFacade(a,b,c,jobs);var admin=new AdminFacade(a,b,c,jobs);Scanner in=new Scanner(System.in);
 while(true){System.out.println("\n1 Success | 2 Fail B | 3 Fail C | 4 Pause after A | 5 Resume | 6 Inspect | 7 Manual compensation | 0 Exit");String choice=in.nextLine().trim();if(choice.equals("0"))return;
 try{System.out.print("Unique operation ID: ");String id=in.nextLine().trim();switch(choice){case "1"->facade.start(id,30,25,2);case "2"->facade.start(id,30,50,2);case "3"->facade.start(id,30,25,0);case "4"->facade.pauseAfterA(id,30,25,2);case "5"->facade.resume(id);case "7"->admin.compensate(id);case "6"->{}default->System.out.println("Unknown choice");}System.out.println(admin.inspect(id));}catch(Exception e){System.out.println("ERROR: "+e.getMessage());}}
 }
}
