package ticketmachine;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TicketMachineTest {
	private static final int PRICE = 50; // Une constante

	private TicketMachine machine; // l'objet à tester

	@Before
	public void setUp() {
		machine = new TicketMachine(PRICE); // On initialise l'objet à tester
	}

	@Test
	// On vérifie que le prix affiché correspond au paramètre passé lors de l'initialisation
	// S1 : le prix affiché correspond à l’initialisation
	public void priceIsCorrectlyInitialized() {
		// Paramètres : message si erreur, valeur attendue, valeur réelle
		assertEquals("Initialisation incorrecte du prix", PRICE, machine.getPrice());
	}

	@Test
	// S2 : la balance change quand on insère de l’argent
	public void insertMoneyChangesBalance() {
		machine.insertMoney(10);
		machine.insertMoney(20);
		assertEquals("La balance n'est pas correctement mise à jour", 10 + 20, machine.getBalance()); // Les montants ont été correctement additionnés               
	}
        
        @Test
        // S3 : on n’imprime pas le ticket si le montant inséré est insuffisant
        public void notEnoughtMoneyToPrintTicket() {
            machine.insertMoney(PRICE-1);
            assertFalse("Montant insuffisant",machine.printTicket());
        }
        
        @Test
        // S4 : on imprime le ticket si le montant inséré est suffisant
        public void enoughtMoneyToPrintTicket() {
            machine.insertMoney(PRICE);
            assertTrue(machine.printTicket());
        }
        
        @Test
        // S5 : Quand on imprime un ticket la balance est décrémentée du prix du ticket
        public void decreaseBalanceWhenPrintTicket(){
            machine.insertMoney(80);
            machine.printTicket();
            assertEquals("Balance mal décrémenté",80-PRICE,machine.getBalance());
        }
        
        @Test
        // S6 : Le montant collecté est mis à jour quand on imprime un ticket(pas avant)
        public void amountCollectNotUpgrade(){
            assertEquals(0,machine.getTotal());
            machine.insertMoney(PRICE*2);
            assertEquals(0, machine.getTotal());
            machine.printTicket();
            machine.printTicket();
            assertEquals(PRICE*2,machine.getTotal());
        }
        
        @Test
        // S7 : refund() rend correctement la monnaie
        public void giveBackMoney(){
            machine.insertMoney(PRICE+20);
            assertEquals(PRICE+20,machine.refund());
        }
        
        @Test
        // S8 : refund() remet la balance à zéro
        public void resetBalance(){
            machine.insertMoney(80);
            machine.printTicket();
            assertEquals(80-PRICE,machine.refund());
            assertEquals(0,machine.getBalance());
        }
        
        @Test
        // S9 : on ne peut pas insérer un montant négatif
        public void negativAmount(){
            try {
                machine.insertMoney(-1);
		fail();
            }catch(IllegalArgumentException e) {
            }
	}
        
        @Test
        // S10 : on ne peut pas créer de machine qui délivre des tickets dont le prix est négatif
        //
        public void negativTicketPrice(){
            try {
                new TicketMachine(0);
		fail();
            } catch (IllegalArgumentException e) {
            }
	}	
}
