(define (cube x) (* x x x))

(define (inc n) (+ n 1)) 

(define (sum term a next b)
    (define (iter a result)
        (if (> a b)
            result
            (iter (next a) (+ (term a) result))))
    (iter a 0))

(sum cube 1 inc 3)