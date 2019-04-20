(load "fast-prime.rkt")

(define (time-prime-test n)
    (newline)
    (display n)
    (start-prime-test n (runtime)))

(define (start-prime-test n start-time)
    (if (fast-prime? n 100)
        (report-prime (- (runtime) start-time))))

(define (report-prime elapsed-time )
    (display " *** ")
    (display elapsed-time))

(define (search-for-primes first last)
    (define (search-iter cur last)
        (if (<= cur last)
            (time-prime-test cur))
        (if (<= cur last) 
            (search-iter (+ cur 2) last)))
    (search-iter (if (even? first) (+ first 1) first)
                 (if (even? last) (- last 1) last)))

(search-for-primes 1000 100000)