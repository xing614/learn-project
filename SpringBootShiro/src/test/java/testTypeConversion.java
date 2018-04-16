import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import demo.util.RespCode;
import demo.util.ReturnJsonBody;
import demo.util.TypeConversion;

@RunWith(SpringJUnit4ClassRunner.class)
public class testTypeConversion {
	 @Test
	public void test() throws IllegalAccessException {
		 ReturnJsonBody bjb = new ReturnJsonBody(44,"2","ok");
		 System.out.println("sys=="+bjb.toString());
		 ReturnJsonBody bjb2 = new ReturnJsonBody(RespCode.UserError);
		 System.out.println("sys2=="+bjb2.toString());		 
	}
}
