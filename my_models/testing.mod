MODEL="covid 19"
VERSION="testing"
OUTPUT="covid"

TYPE=Constant
RUNS=1
STEPS=100
PRECISION=1.0


COMPARTMENT gesunde=1000000
COMPARTMENT infizierte=1
COMPARTMENT immune=0


PARAMETER ansteckungsrate = 0.000001
PARAMETER immunisierungsrate = 0.1

DELTA gesunde = -gesunde*infizierte*ansteckungsrate
DELTA infizierte = gesunde*infizierte*ansteckungsrate - immunisierungsrate*infizierte
DELTA immune = immunisierungsrate*infizierte


