(load "ex_37.rkt")

(cont-frac (lambda (i) 1) 
           (lambda (i) 
                     (if (= 0 (remainder (+ i 1) 3))
            (* 2 (/ (+ i 1) 3))
            1)) 
           10)