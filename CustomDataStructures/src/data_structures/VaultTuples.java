package data_structures;

public class VaultTuples {
	
	public interface IVaultValue01<T> {
		abstract void setVal1(Object key, T val);
		abstract T getVal1();
	}
	
	public interface IVaultValue02<T> extends IVaultValue01<T> {
		abstract void setVal2(Object key, T val);
		abstract T getVal2();
	}
	
	public interface IVaultValue03<T> extends IVaultValue02<T> {
		abstract void setVal3(Object key, T val);
		abstract T getVal3();
	}
	
	public interface IVaultValue04<T> extends IVaultValue03<T> {
		abstract void setVal4(Object key, T val);
		abstract T getVal4();
	}
	
	public interface IVaultValue05<T> extends IVaultValue04<T> {
		abstract void setVal5(Object key, T val);
		abstract T getVal5();
	}

	public interface IVaultValue06<T> extends IVaultValue05<T> {
		abstract void setVal6(Object key, T val);
		abstract T getVal6();
	}
	
	public interface IVaultValue07<T> extends IVaultValue06<T> {
		abstract void setVal7(Object key, T val);
		abstract T getVal7();
	}
	
	public interface IVaultValue08<T> extends IVaultValue07<T> {
		abstract void setVal8(Object key, T val);
		abstract T getVal8();
	}
	
	public interface IVaultValue09<T> extends IVaultValue08<T> {
		abstract void setVal9(Object key, T val);
		abstract T getVal9();
	}
	
	public interface IVaultValue10<T> extends IVaultValue09<T> {
		abstract void setVal10(Object key, T val);
		abstract T getVal10();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	// Vault Tuples stores values and a modify key to permit change of stored elements.
	
	public static class VaultTuple1<T1> implements IVaultValue01<T1> {
		
		private final Object modifyKey;
		private T1 val1;
		
		public VaultTuple1(Object key, T1 val1) {
			modifyKey = key;
			this.val1 = val1;
		}
		
		public void setVal1(Object key, T1 val) {
			if (modifyKey.equals(key))
				val1 = val;
		}
		
		public T1 getVal1() {
			return val1;
		}
		
	}
	
	public static class VaultTuple2<T1, T2> 
				implements IVaultValue01<T1>,
						   IVaultValue02<T2> {
		
		private final Object modifyKey;
		private T1 val1;
		private T2 val2;
		
		public VaultTuple2(Object key, T1 val1, T2 val2) {
			modifyKey = key;
			this.val1 = val1;
			this.val2 = val2;
		}
		
		public void setVal1(Object key, T1 val) {
			if (modifyKey.equals(key))
				val1 = val;
		}
		
		public void setVal2(Object key, T2 val) {
			if (modifyKey.equals(key))
				val2 = val;
		}
		
		public T1 getVal1() {
			return val1;
		}
		
		public T2 getVal2() {
			return val2;
		}
	}
	
	public static class VaultTuple3<T1, T2, T3> 
				implements IVaultValue01<T1>,
						   IVaultValue02<T2>,
						   IVaultValue03<T3> {
		
		private final Object modifyKey;
		private T1 val1;
		private T2 val2;
		private T3 val3;
		
		public VaultTuple3(Object key, T1 val1, T2 val2, T3 val3) {
			modifyKey = key;
			this.val1 = val1;
			this.val2 = val2;
			this.val3 = val3;
		}
		
		public void setVal1(Object key, T1 val) {
			if (modifyKey.equals(key))
				val1 = val;
		}
		
		public void setVal2(Object key, T2 val) {
			if (modifyKey.equals(key))
				val2 = val;
		}
		
		public void setVal3(Object key, T3 val) {
			if (modifyKey.equals(key))
				val3 = val;
		}
		
		public T1 getVal1() {
			return val1;
		}
		
		public T2 getVal2() {
			return val2;
		}
		
		public T3 getVal3() {
			return val3;
		}
	}
	
	public static class VaultTuple4<T1, T2, T3, T4> 
				implements IVaultValue01<T1>,
				   		   IVaultValue02<T2>,
				   		   IVaultValue03<T3>,
				   		   IVaultValue04<T4> {
		
		private final Object modifyKey;
		private T1 val1;
		private T2 val2;
		private T3 val3;
		private T4 val4;
		
		public VaultTuple4(Object key, T1 val1, T2 val2, T3 val3, T4 val4) {
			modifyKey = key;
			this.val1 = val1;
			this.val2 = val2;
			this.val3 = val3;
			this.val4 = val4;
		}
		
		public void setVal1(Object key, T1 val) {
			if (modifyKey.equals(key))
				val1 = val;
		}
		
		public void setVal2(Object key, T2 val) {
			if (modifyKey.equals(key))
				val2 = val;
		}
		
		public void setVal3(Object key, T3 val) {
			if (modifyKey.equals(key))
				val3 = val;
		}
		
		public void setVal4(Object key, T4 val) {
			if (modifyKey.equals(key))
				val4 = val;
		}
		
		public T1 getVal1() {
			return val1;
		}
		
		public T2 getVal2() {
			return val2;
		}
		
		public T3 getVal3() {
			return val3;
		}
		
		public T4 getVal4() {
			return val4;
		}
	}
	
	public static class VaultTuple5<T1, T2, T3, T4, T5> 
				implements IVaultValue01<T1>,
						   IVaultValue02<T2>,
						   IVaultValue03<T3>,
						   IVaultValue04<T4>,
						   IVaultValue05<T5> {
		
		private final Object modifyKey;
		private T1 val1;
		private T2 val2;
		private T3 val3;
		private T4 val4;
		private T5 val5;
		
		public VaultTuple5(Object key, T1 val1, T2 val2, T3 val3, T4 val4, T5 val5) {
			modifyKey = key;
			this.val1 = val1;
			this.val2 = val2;
			this.val3 = val3;
			this.val4 = val4;
			this.val5 = val5;
		}
		
		public void setVal1(Object key, T1 val) {
			if (modifyKey.equals(key))
				val1 = val;
		}
		
		public void setVal2(Object key, T2 val) {
			if (modifyKey.equals(key))
				val2 = val;
		}
		
		public void setVal3(Object key, T3 val) {
			if (modifyKey.equals(key))
				val3 = val;
		}
		
		public void setVal4(Object key, T4 val) {
			if (modifyKey.equals(key))
				val4 = val;
		}
		
		public void setVal5(Object key, T5 val) {
			if (modifyKey.equals(key))
				val5 = val;
		}
		
		public T1 getVal1() {
			return val1;
		}
		
		public T2 getVal2() {
			return val2;
		}
		
		public T3 getVal3() {
			return val3;
		}
		
		public T4 getVal4() {
			return val4;
		}
		
		public T5 getVal5() {
			return val5;
		}
	}
	
	public static class VaultTuple6<T1, T2, T3, T4, T5, T6> 
				implements IVaultValue01<T1>,
						   IVaultValue02<T2>,
						   IVaultValue03<T3>,
						   IVaultValue04<T4>,
						   IVaultValue05<T5>,
						   IVaultValue06<T6> {
	
		private final Object modifyKey;
		private T1 val1;
		private T2 val2;
		private T3 val3;
		private T4 val4;
		private T5 val5;
		private T6 val6;
		
		public VaultTuple6(Object key, T1 val1, T2 val2, T3 val3, T4 val4, T5 val5, T6 val6) {
			modifyKey = key;
			this.val1 = val1;
			this.val2 = val2;
			this.val3 = val3;
			this.val4 = val4;
			this.val5 = val5;
			this.val6 = val6;
		}
		
		public void setVal1(Object key, T1 val) {
			if (modifyKey.equals(key))
				val1 = val;
		}
		
		public void setVal2(Object key, T2 val) {
			if (modifyKey.equals(key))
				val2 = val;
		}
		
		public void setVal3(Object key, T3 val) {
			if (modifyKey.equals(key))
				val3 = val;
		}
		
		public void setVal4(Object key, T4 val) {
			if (modifyKey.equals(key))
				val4 = val;
		}
		
		public void setVal5(Object key, T5 val) {
			if (modifyKey.equals(key))
				val5 = val;
		}
		
		public void setVal6(Object key, T6 val) {
			if (modifyKey.equals(key))
				val6 = val;
		}
		
		public T1 getVal1() {
			return val1;
		}
		
		public T2 getVal2() {
			return val2;
		}
		
		public T3 getVal3() {
			return val3;
		}
		
		public T4 getVal4() {
			return val4;
		}
		
		public T5 getVal5() {
			return val5;
		}
		
		public T6 getVal6() {
			return val6;
		}
	}
	
	public static class VaultTuple7<T1, T2, T3, T4, T5, T6, T7> 
				implements IVaultValue01<T1>,
						   IVaultValue02<T2>,
						   IVaultValue03<T3>,
						   IVaultValue04<T4>,
						   IVaultValue05<T5>,
						   IVaultValue06<T6>,
						   IVaultValue07<T7> {
	
		private final Object modifyKey;
		private T1 val1;
		private T2 val2;
		private T3 val3;
		private T4 val4;
		private T5 val5;
		private T6 val6;
		private T7 val7;
		
		public VaultTuple7(Object key, T1 val1, T2 val2, T3 val3, T4 val4, T5 val5, T6 val6, T7 val7) {
			modifyKey = key;
			this.val1 = val1;
			this.val2 = val2;
			this.val3 = val3;
			this.val4 = val4;
			this.val5 = val5;
			this.val6 = val6;
			this.val7 = val7;
		}
		
		public void setVal1(Object key, T1 val) {
			if (modifyKey.equals(key))
				val1 = val;
		}
		
		public void setVal2(Object key, T2 val) {
			if (modifyKey.equals(key))
				val2 = val;
		}
		
		public void setVal3(Object key, T3 val) {
			if (modifyKey.equals(key))
				val3 = val;
		}
		
		public void setVal4(Object key, T4 val) {
			if (modifyKey.equals(key))
				val4 = val;
		}
		
		public void setVal5(Object key, T5 val) {
			if (modifyKey.equals(key))
				val5 = val;
		}
		
		public void setVal6(Object key, T6 val) {
			if (modifyKey.equals(key))
				val6 = val;
		}
		
		public void setVal7(Object key, T7 val) {
			if (modifyKey.equals(key))
				val7 = val;
		}
		
		public T1 getVal1() {
			return val1;
		}
		
		public T2 getVal2() {
			return val2;
		}
		
		public T3 getVal3() {
			return val3;
		}
		
		public T4 getVal4() {
			return val4;
		}
		
		public T5 getVal5() {
			return val5;
		}
		
		public T6 getVal6() {
			return val6;
		}
		
		public T7 getVal7() {
			return val7;
		}
	}	

	public static class VaultTuple8<T1, T2, T3, T4, T5, T6, T7, T8> 
				implements IVaultValue01<T1>,
						   IVaultValue02<T2>,
						   IVaultValue03<T3>,
						   IVaultValue04<T4>,
						   IVaultValue05<T5>,
						   IVaultValue06<T6>,
						   IVaultValue07<T7>,
						   IVaultValue08<T8> {
	
		private final Object modifyKey;
		private T1 val1;
		private T2 val2;
		private T3 val3;
		private T4 val4;
		private T5 val5;
		private T6 val6;
		private T7 val7;
		private T8 val8;
		
		public VaultTuple8(Object key, T1 val1, T2 val2, T3 val3, T4 val4, T5 val5, T6 val6, T7 val7, T8 val8) {
			modifyKey = key;
			this.val1 = val1;
			this.val2 = val2;
			this.val3 = val3;
			this.val4 = val4;
			this.val5 = val5;
			this.val6 = val6;
			this.val7 = val7;
			this.val8 = val8;
		}
		
		public void setVal1(Object key, T1 val) {
			if (modifyKey.equals(key))
				val1 = val;
		}
		
		public void setVal2(Object key, T2 val) {
			if (modifyKey.equals(key))
				val2 = val;
		}
		
		public void setVal3(Object key, T3 val) {
			if (modifyKey.equals(key))
				val3 = val;
		}
		
		public void setVal4(Object key, T4 val) {
			if (modifyKey.equals(key))
				val4 = val;
		}
		
		public void setVal5(Object key, T5 val) {
			if (modifyKey.equals(key))
				val5 = val;
		}
		
		public void setVal6(Object key, T6 val) {
			if (modifyKey.equals(key))
				val6 = val;
		}
		
		public void setVal7(Object key, T7 val) {
			if (modifyKey.equals(key))
				val7 = val;
		}
		
		public void setVal8(Object key, T8 val) {
			if (modifyKey.equals(key))
				val8 = val;
		}
		
		public T1 getVal1() {
			return val1;
		}
		
		public T2 getVal2() {
			return val2;
		}
		
		public T3 getVal3() {
			return val3;
		}
		
		public T4 getVal4() {
			return val4;
		}
		
		public T5 getVal5() {
			return val5;
		}
		
		public T6 getVal6() {
			return val6;
		}
		
		public T7 getVal7() {
			return val7;
		}
		
		public T8 getVal8() {
			return val8;
		}
	}	
	
	
	
	public static class VaultTuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9> 
				implements IVaultValue01<T1>,
						   IVaultValue02<T2>,
						   IVaultValue03<T3>,
						   IVaultValue04<T4>,
						   IVaultValue05<T5>,
						   IVaultValue06<T6>,
						   IVaultValue07<T7>,
						   IVaultValue08<T8>,
						   IVaultValue09<T9> {
	

		private final Object modifyKey;
		private T1 val1;
		private T2 val2;
		private T3 val3;
		private T4 val4;
		private T5 val5;
		private T6 val6;
		private T7 val7;
		private T8 val8;
		private T9 val9;
		
		public VaultTuple9(Object key, T1 val1, T2 val2, T3 val3, T4 val4, T5 val5, T6 val6, T7 val7, T8 val8, T9 val9) {
			modifyKey = key;
			this.val1 = val1;
			this.val2 = val2;
			this.val3 = val3;
			this.val4 = val4;
			this.val5 = val5;
			this.val6 = val6;
			this.val7 = val7;
			this.val8 = val8;
			this.val9 = val9;
		}
		
		public void setVal1(Object key, T1 val) {
			if (modifyKey.equals(key))
				val1 = val;
		}
		
		public void setVal2(Object key, T2 val) {
			if (modifyKey.equals(key))
				val2 = val;
		}
		
		public void setVal3(Object key, T3 val) {
			if (modifyKey.equals(key))
				val3 = val;
		}
		
		public void setVal4(Object key, T4 val) {
			if (modifyKey.equals(key))
				val4 = val;
		}
		
		public void setVal5(Object key, T5 val) {
			if (modifyKey.equals(key))
				val5 = val;
		}
		
		public void setVal6(Object key, T6 val) {
			if (modifyKey.equals(key))
				val6 = val;
		}
		
		public void setVal7(Object key, T7 val) {
			if (modifyKey.equals(key))
				val7 = val;
		}
		
		public void setVal8(Object key, T8 val) {
			if (modifyKey.equals(key))
				val8 = val;
		}
		
		public void setVal9(Object key, T9 val) {
			if (modifyKey.equals(key))
				val9 = val;
		}
		
		public T1 getVal1() {
			return val1;
		}
		
		public T2 getVal2() {
			return val2;
		}
		
		public T3 getVal3() {
			return val3;
		}
		
		public T4 getVal4() {
			return val4;
		}
		
		public T5 getVal5() {
			return val5;
		}
		
		public T6 getVal6() {
			return val6;
		}
		
		public T7 getVal7() {
			return val7;
		}
		
		public T8 getVal8() {
			return val8;
		}
		
		public T9 getVal9() {
			return val9;
		}
	}	
	
	
	
	public static class VaultTuple10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> 
				implements IVaultValue01<T1>,
						   IVaultValue02<T2>,
						   IVaultValue03<T3>,
						   IVaultValue04<T4>,
						   IVaultValue05<T5>,
						   IVaultValue06<T6>,
						   IVaultValue07<T7>,
						   IVaultValue08<T8>,
						   IVaultValue09<T9>,
						   IVaultValue10<T10> {
	
		private final Object modifyKey;
		private T1 val1;
		private T2 val2;
		private T3 val3;
		private T4 val4;
		private T5 val5;
		private T6 val6;
		private T7 val7;
		private T8 val8;
		private T9 val9;
		private T10 val10;
		
		public VaultTuple10(Object key, T1 val1, T2 val2, T3 val3, T4 val4, T5 val5, T6 val6, T7 val7, T8 val8, T9 val9, T10 val10) {
			modifyKey = key;
			this.val1 = val1;
			this.val2 = val2;
			this.val3 = val3;
			this.val4 = val4;
			this.val5 = val5;
			this.val6 = val6;
			this.val7 = val7;
			this.val8 = val8;
			this.val9 = val9;
			this.val10 = val10;
		}
		
		public void setVal1(Object key, T1 val) {
			if (modifyKey.equals(key))
				val1 = val;
		}
		
		public void setVal2(Object key, T2 val) {
			if (modifyKey.equals(key))
				val2 = val;
		}
		
		public void setVal3(Object key, T3 val) {
			if (modifyKey.equals(key))
				val3 = val;
		}
		
		public void setVal4(Object key, T4 val) {
			if (modifyKey.equals(key))
				val4 = val;
		}
		
		public void setVal5(Object key, T5 val) {
			if (modifyKey.equals(key))
				val5 = val;
		}
		
		public void setVal6(Object key, T6 val) {
			if (modifyKey.equals(key))
				val6 = val;
		}
		
		public void setVal7(Object key, T7 val) {
			if (modifyKey.equals(key))
				val7 = val;
		}
		
		public void setVal8(Object key, T8 val) {
			if (modifyKey.equals(key))
				val8 = val;
		}
		
		public void setVal9(Object key, T9 val) {
			if (modifyKey.equals(key))
				val9 = val;
		}
		
		public void setVal10(Object key, T10 val) {
			if (modifyKey.equals(key))
				val10 = val;
		}
		
		public T1 getVal1() {
			return val1;
		}
		
		public T2 getVal2() {
			return val2;
		}
		
		public T3 getVal3() {
			return val3;
		}
		
		public T4 getVal4() {
			return val4;
		}
		
		public T5 getVal5() {
			return val5;
		}
		
		public T6 getVal6() {
			return val6;
		}
		
		public T7 getVal7() {
			return val7;
		}
		
		public T8 getVal8() {
			return val8;
		}
		
		public T9 getVal9() {
			return val9;
		}
		
		public T10 getVal10() {
			return val10;
		}
	}	
	
}
