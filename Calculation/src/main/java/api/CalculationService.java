package api;


import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import java.util.List;
import javax.ws.rs.core.MediaType;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import ejbs.calculation;

@Stateless
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("cal")
public class CalculationService {

	
    @PersistenceContext(unitName="calc")
    private EntityManager entityManager;
    @Path("calc")
    @POST
    public int calculate(calculation calc) throws ArithmeticException, IllegalArgumentException {
    	entityManager.persist(calc);
        int number1 = calc.getNumber1();
        int number2 = calc.getNumber2();
        String operation = calc.getOperation();

        int result;
        if ("+".equals(operation)) {
            result = number1 + number2;
            return result;
        } else if ("-".equals(operation)) {
            result = number1 - number2;
            return result;
        } else if ("*".equals(operation)) {
            result = number1 * number2;
            return result;
        } else if ("/".equals(operation)) {
            if (number2 != 0) {
                result = number1 / number2;
                return result;
            } else {
                throw new ArithmeticException("Division by zero");
            }
        } else {
            throw new IllegalArgumentException("Invalid operation");
        }
            

}
    
    @GET
    @Path("calculations")
    public List<calculation> getCalculations(){
    	
        TypedQuery<calculation> query = entityManager.createQuery("SELECT calc FROM calculation calc", calculation.class);
        
        List<calculation> calculations = query.getResultList();
        
        return calculations;
    	
    	
    	
    }
 
}

