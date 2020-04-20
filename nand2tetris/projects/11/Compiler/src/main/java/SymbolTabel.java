import constant.Kind;
import entity.Symbol;

import java.util.HashMap;
import java.util.Map;

public class SymbolTabel {
    private HashMap<String, Symbol> subroutineTable;
    private HashMap<String, Symbol> classTable;

    public SymbolTabel() {
        this.subroutineTable = new HashMap<String, Symbol>();
        this.classTable = new HashMap<String, Symbol>();
    }

    public void startSubroutine() {
        this.subroutineTable.clear();
    }

    public void define(String name, String type, Kind kind) {
        Integer index = this.varCount(kind);
        Symbol symbol = new Symbol(name, type, kind, index);
        if (kind.equals(Kind.FIELD) || kind.equals(Kind.STATIC)) {
            subroutineTable.put(name, symbol);
        } else if (kind.equals(Kind.VAR) || kind.equals(Kind.ARGUMENT)) {
            classTable.put(name, symbol);
        }
    }

    public Integer varCount(Kind kind) {
        int n = 0;
        if (kind.equals(Kind.FIELD) || kind.equals(Kind.STATIC)) {
            for (Map.Entry s: subroutineTable.entrySet()) {
                if (((Symbol)s.getValue()).getKind().equals(kind)) {
                    n += 1;
                }
            }
        } else if (kind.equals(Kind.VAR) || kind.equals(Kind.ARGUMENT)) {
            for (Map.Entry s: subroutineTable.entrySet()) {
                if (((Symbol)s.getValue()).getKind().equals(kind)) {
                    n += 1;
                }
            }
        }
        return n;
    }


    public Kind kindOf(String name) {
        if (subroutineTable.get(name) != null) {
            return subroutineTable.get(name).getKind();
        } else if (classTable.get(name) != null) {
            return classTable.get(name).getKind();
        } else {
            return null;
        }
    }

    public String typeOf(String name) {
        if (subroutineTable.get(name) != null) {
            return subroutineTable.get(name).getType();
        } else if (classTable.get(name) != null) {
            return classTable.get(name).getType();
        } else {
            return null;
        }
    }

    public Integer indexOf(String name) {
        if (subroutineTable.get(name) != null) {
            return subroutineTable.get(name).getIndex();
        } else if (classTable.get(name) != null) {
            return classTable.get(name).getIndex();
        } else {
            return null;
        }
    }
}
