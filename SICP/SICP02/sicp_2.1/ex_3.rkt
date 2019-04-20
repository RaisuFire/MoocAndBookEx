(load "ex_2.rkt")

(define (make-rectangle length width)
    (cons length width))

(define (length-rectangle r)
    (car r))

(define (width-rectangle r)
    (cdr r))

(define (length-of rectangle rectangle)
    (let ((length (length-rectangle)))
        ()))

(define (perimeter rectangle)
    (* (+ (car rectangle) (cdr rectangle) 2))

(define (area rectangle)
    (* (car rectangle) (cdr rectangle)))