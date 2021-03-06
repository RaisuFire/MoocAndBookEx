
def split(text, sep=None, maxsplit=-1):
    """Like str.split applied to text, but strips whitespace from each piece"""
    return [t.strip() for t in text.strip().split(sep, maxsplit) if t]


def grammar(description, whitespace=r'\s*'):
    """Convert a description to a grammar"""
    G = {' ': whitespace}

    description = description.replace('\t', ' ')
    for line in description.splitlines():
        lhs, rhs = split(line, ' => ', 1)
        alternatives = split(rhs, ' | ')
        G[lhs] = tuple(map(split, alternatives))
    return G

G = grammar(
 """Exp     => Term  [+-]  Exp | Term
    Term    => Factor [*/] Term | Factor
    Factor  => Funcall | Var | Num | [(] Exp [)]
    Funcall => Var [(] Exps [)]
    Exps    => Exp [,] Exps | Exp
    Var     => [a-zA-z]\w*
    Num     => [-+]?[0-9]+([.][0-9]*)?""")

print(G)