(define (+ a b)
    (if (= a 0)
        b
        (inc (+ (dec a) b))))


(define (+ a b)
    (if (= a 0)
        b
        (+ (dec a) (inc b))))

a  b  = 3, 3

ex1; 
(+ 3 3)
(inc (+ 2) 3)
(inc (inc (+ 1) 3))
(inc (inc (inc 3)))
(inc (inc 4))
(inc 5)
6

ex2:
(+ 2 4)
(+ 1 5)
6

