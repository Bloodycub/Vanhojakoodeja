package TestpackCore;

import org.osbot.rs07.api.model.Player;
import org.osbot.rs07.api.ui.EquipmentSlot;
import org.osbot.rs07.script.Script;

import Core.Support.Areas;
import Core.Support.Sleep;


public class Banking extends Script{
	private String BankName;
	private Boolean Deposite;
	private Boolean Withdraw;
	private int Amount;
	private Boolean Depositeall;
	private String Depositeitem;
	private String Withdrawitem;
	Areas areas = new Areas();
	Bankareas bankC = new Bankareas(BankName);
	Player myPlayer;
	public Banking(String bankName,Boolean depositeall) {
		this.BankName = bankName;
		this.Depositeall = depositeall;

	}
	public Banking(String bankName,Boolean deposite,String depositeitem) {
		this.BankName = bankName;
		this.Deposite = deposite;
		this.Depositeitem = depositeitem;
	}
	public Banking(String bankName,Boolean depositeall ,Boolean withdraw ,String withdrawItem,int amount) {
		this.BankName = bankName;
		this.Withdraw = withdraw;
		this.Amount = amount;
		this.Depositeall = depositeall;
		this.Withdrawitem =withdrawItem;
	}
	public Banking(String bankName,Boolean deposite,String depositeitem ,Boolean withdraw ,String withdrawItem,int amount) {
		this.BankName = bankName;
		this.Deposite = deposite;
		this.Withdraw = withdraw;
		this.Amount = amount;
		this.Depositeitem = depositeitem;
		this.Withdrawitem =withdrawItem;
	}
	public Banking(String bankName,Boolean depositeall ,Boolean deposite,String depositeitem ,Boolean withdraw ,String withdrawItem,int amount) {
		this.BankName = bankName;
		this.Deposite = deposite;
		this.Withdraw = withdraw;
		this.Amount = amount;
		this.Depositeall = depositeall;
		this.Depositeitem = depositeitem;
		this.Withdrawitem =withdrawItem;
	}
	
	public void DoingBank() throws InterruptedException {
		if(!bankC.BankContainsmyPlayer(myPlayer())) {
			walking.webWalk(bankC.GetBank());
			sleep(random(1500,2000));
		}
		if(!bank.isOpen()) {
			bank.open();
			sleep(random(200,600));
			Sleep.waitCondition(() -> bank.isOpen(), 200,3000);
		}
		if(bank.isOpen()){
			if(Deposite == true && Depositeall == false) {
				if(getEquipment().isWearingItem(EquipmentSlot.WEAPON) == true) {
				bank.depositWornItems();
				sleep(random(200,600));
				Sleep.waitCondition(() -> getEquipment().isWearingItem(EquipmentSlot.WEAPON) == false, 200,2000);
				}
				bank.depositAll(this.Depositeitem);
				sleep(random(200,600));
				Sleep.waitCondition(() -> !inventory.contains(Depositeitem), 200,3000);
			}
			if(!inventory.isEmpty() && Depositeall == true) {
				bank.depositAll();
				sleep(random(200,600));
				Sleep.waitCondition(() -> inventory.isEmpty(), 200,2000);
				bank.depositWornItems();
				sleep(random(200,600));
				Sleep.waitCondition(() -> getEquipment().isWearingItem(EquipmentSlot.WEAPON) == false, 200,2000);
			}
			if(Withdraw == true) {
				String all = Integer.toString(Amount);
				if(all.contentEquals("All")){
				bank.withdrawAll(Withdrawitem);
				sleep(random(200,600));
				Sleep.waitCondition(() -> inventory.contains(Withdrawitem), 200,2000);
				}else {
					bank.withdraw(Withdrawitem, Amount);
					sleep(random(200,600));
					Sleep.waitCondition(() -> inventory.contains(Withdrawitem), 200,2000);
				}
			}
		}
		bank.close();
		sleep(random(200,400));
		Sleep.waitCondition(() -> !bank.isOpen(), 200,2000);
	}
	
	
	
	
	
	
	@Override
	public int onLoop() throws InterruptedException {
		return 0;
	}

}
