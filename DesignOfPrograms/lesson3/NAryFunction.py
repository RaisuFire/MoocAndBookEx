# ---------------
# User Instructions
#
# Write a function, n_ary(f), that takes a binary function (a function
# that takes 2 inputs) as input and returns an n_ary function.

from functools import update_wrapper


def decorator(d):
    "make function d a decorator : d wraps a function fn."

    def _d(fn):
        return update_wrapper(d(fn), fn)

    update_wrapper(_d, d)
    return _d


@decorator
def n_ary(f):
    """Given binary function f(x, y), return an n_ary function such
    that f(x, y, z) = f(x, f(y,z)), etc. Also allow f(x) = x."""

    def n_ary_f(x, *args):
        if not args:
            return x
        else:
            return f(x, n_ary_f(*args))

    return n_ary_f


@n_ary
def seq(x, y):
    return ('seq', x, y)


@decorator
def meno(f):
    """Decorator that caches the return value for each call to f(*args)
    then when called again with same args, we can just look it up"""



@decorator
def countcalls(f):
    "Decorator that makes the function count calls to do it, in callcounts[f]"

    def _f(*args):
        callcounts[_f] += 1
        return f(*args)

    callcounts[_f] = 0
    return _f




@countcalls
def fib(n):
    return 1 if n <= 1 else fib(n-1) + fib(n-2)


callcounts = {}
# seq = n_ary(seq)

help(seq)
