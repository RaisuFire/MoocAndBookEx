(define tolerance 0.000001) 
  
(define (fixed-point f first-guess) 
  (define (close-enough? v1 v2) 
    (< (abs (- v1 v2)) tolerance)) 
    (define (try guess) 
      (print-line guess) 
      (let ((next (f guess))) 
        (if (close-enough? guess next) 
            next 
            (try next)))) 
    (try first-guess)) 

(define (print-line value) 
  (display value) 
  (newline)) 

(define (x-to-the-x y) 
  (fixed-point (lambda (x) (/ (log y) (log x))) 
    10.0)) 

;(fixed-point (lambda (x) (+ 1 (/ 1 x))) 1.0)

;(fixed-point (lambda (x) (/ (log y) (log x))) 10.0)

; (x-to-the-x 1000)

