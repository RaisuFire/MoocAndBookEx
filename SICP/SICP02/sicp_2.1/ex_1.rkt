(define (abs n)
  (if (< n 0)
      (- n)
      n))

(define (numer x) (car x)) 
  
(define (denom x) (cdr x)) 
  
(define (print-rat x) 
  (newline) 
  (display (numer x)) 
  (display "/") 
  (display (denom x))) 

(define (make-rat n d)
    (if (< (/ n d) 0)
         (cons (- (abs n)) (abs d))
         (cons (abs n) (abs d))))

(print-rat (make-rat 2 3))
(print-rat (make-rat -2 3))
(print-rat (make-rat 2 -3))
(print-rat (make-rat -2 -3))
