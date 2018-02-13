package net.itca.datastructure;

import org.jetbrains.annotations.Nullable;

/**
 * Created by dylan on 13.02.18.
 * An immutable 2-tuple
 */
public class Tuple<A, B> {

    private final A a;
    private final B b;

    public Tuple(@Nullable A a, @Nullable  B b) {
        this.a = a;
        this.b = b;
    }

    public A getA(){
        return a;
    }

    public B getB(){
        return b;
    }

}
