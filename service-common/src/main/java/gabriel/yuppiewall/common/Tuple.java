package gabriel.yuppiewall.common;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Tuple<K, V> implements Serializable {

	private K key;
	private V value;

	public Tuple() {
	}

	public Tuple(K k) {
		this.key = k;
	}

	public Tuple(K k, V v) {
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

	public void setKey(K key) {
		this.key = key;
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
		Tuple other = (Tuple) obj;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		return true;
	}
}
