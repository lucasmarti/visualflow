# A b
# https://de.wikipedia.org/wiki/SIR-Modell
#
#
MODEL="SIR for covid 19"
VERSION="testing"
OUTPUT="covid16"

# make a deterministic analysis
TYPE=Constant
# run the simulation 100 times
RUNS=100
# simulate the model 40 steps
STEPS=40

# only integer numbers
PRECISION=1.0

# number of susceptible individuals at the beginning
COMPARTMENT s = 999999

# number of infected individuals at the beginning
COMPARTMENT i=1

# number of recovered or deceased (or immune) individuals at the beginning
COMPARTMENT r=0

# number of individuals in the population 
PARAMETER n = 1000000

# infection rate
PARAMETER beta = 1.6

# recovery rate
PARAMETER gamma = 0.5

# the differential equations
DELTA s = -(s*i)*POW(n,-1)*beta
DELTA i = (s*i)*POW(n,-1)*beta - i*gamma
DELTA r = i*gamma
