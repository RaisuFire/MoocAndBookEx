(load "inc.rkt")
(load "square.rkt")
      
(define (compose f g)
    (lambda (x) (f (g x))))

;((compose square inc) 6)
