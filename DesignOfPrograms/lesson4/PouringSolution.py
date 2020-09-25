def successors(x, y, X, Y):
    assert x <= X and y <= Y
    return {((0, y + x) if y + x <= Y else (x - (Y - y), y + (Y - y))): 'X->Y',
            ((x + y, 0) if x + y <= X else (x + (X - x), y - (X - x))): 'Y->X',
            (X, y): 'fill X', (x, Y): 'fill Y',
            (0, y): 'empty X', (x, 0): 'empty Y'}


def pour_problem(X, Y, goal, start=(0, 0)):
    if goal in start:
        return [start]
    explored = set()
    frontier = [ [start] ]
    while frontier:
        path = frontier.pop(0)
        (x, y) = path[-1]
        for (state, action) in successors(x, y, X, Y).items():
            if state not in explored:
                path2 = path + [action, state]
                if goal in state:
                    return path2
                else:
                    frontier.append(path2)

    return Fail


Fail = []
