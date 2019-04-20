; (define (cont-frac n d k)
;     (define (loop result term)
;         (if (= term 0)
;             result
;             (loop (/ n term)
;                      (+ (d term) result))
;                     (- term 1)))
;     (loop 0 k))

(define (cont-frac n d k)
    (display "  n k ===>")
    (display (n k))
    (display "  d k ===>")
    (display (d k))
    (newline)
    (if (= k 0)
        0
        (/ (n k) (+ (d k) (cont-frac n d (- k 1))))))

; (cont-frac (lambda (i) 1.0)
;            (lambda (i) 1.0)
;            10)

