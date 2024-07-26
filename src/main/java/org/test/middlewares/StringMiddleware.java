package org.test.middlewares;

public abstract class StringMiddleware {
    private StringMiddleware next;

    public static StringMiddleware link(StringMiddleware first, StringMiddleware... chain) {
        StringMiddleware head = first;
        for (StringMiddleware nextInChain : chain) {
            head.next = nextInChain;
            head = nextInChain;
        }
        return first;
    }

    public boolean check(String text) {
        if (next != null) {
            return checkNext(text);
        }
        return false;
    }

    public boolean checkNext(String text) {
        if (next == null) {
            return false;
        }
        return next.check(text);
    }

}
