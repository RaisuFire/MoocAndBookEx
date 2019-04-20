(load "ex_42.rkt")
(load "square.rkt")

(define (repeated f n)
    (if (< n 1)
        (lambda (x) x) 
        (compose f (repeated f (- n 1)))))


;((repeated square 2) 5)