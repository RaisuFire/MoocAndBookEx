 
(define (cube x) (* x x x)) 
  
(define (inc n) (+ n 1)) 
     
(define (sum term a next b) 
    (if (> a b) 
        0 
        (+ (term a) 
            (sum term (next a) next b)))) 

(define (integral f a b dx)
    (define (add-dx x) (+ x dx))
    (* (sum f (+ a(/ dx 2.0)) add-dx b)
        dx))

(define (round-to-next-even x) 
    (+ x (remainder x 2))) 

(define (simpson f a b n)
    (define h (/ (- b a) n))
    (define (simpson-term k)
        (define y (f (+ a (* k h))))
        (if (or (= k 0) (= k n))
            (* 1 y)
            (if (even? k)
                (* 2 y)
                (* 4 y))))
    (* (/ h 3) (sum simpson-term 0 inc n)))

(simpson cube 0 1 100)
(integral cube 0 1 0.001)