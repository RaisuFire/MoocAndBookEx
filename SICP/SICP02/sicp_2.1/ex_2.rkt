(define (make-segment start-segment end-segment)
    (cons start-segment end-segment))

(define (start-segment x)
    (car x))

(define (end-segment x)
    (cdr x))

(define (make-point x-point y-point)
    (cons x-point y-point))

(define (x-point p)
    (car p))

(define (y-point p)
    (cdr p))

(define (print-point p) 
    (newline) 
    (display "(") 
    (display (x-point p)) 
    (display ",") 
    (display (y-point p)) 
    (display ")")) 

(define (midpoint-segment line)
  (let ((x (car(car line)))
    (y (cdr(car line))))
    (print-point x)
    (print-point y)
    (mid x y)))

(define (mid x1 x2)
    (/ (+ x1 x2) 2))

(define line (make-segment))
(midpoint-segment )
