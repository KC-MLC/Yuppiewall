package gabriel.yuppiewall.common;

public interface Command<T> {

	void execute(T t);

}
