package gabriel.yuppiewall.common;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Tupple<K, V> implements Serializable {

	private K key;
	private V value;

	public Tupple(K k) {
		this.key = k;
	}

	public Tupple(K k, V v) {
		this.key = k;
		this.value = v;
	}

	public V getValue() {
		return value;
	}

	public void setValue(V value) {
		this.value = value;
	}

	public K getKey() {
		return key;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		@SuppressWarnings("rawtypes")
		Tupple other = (Tupple) obj;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		return true;
	}
}
