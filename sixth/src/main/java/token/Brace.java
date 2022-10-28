package token;

import visitor.TokenVisitor;

public abstract class Brace implements Token {
    @Override
    public void accept(TokenVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Brace)) {
            return false;
        }
        return toString().equals(obj.toString());
    }
}
