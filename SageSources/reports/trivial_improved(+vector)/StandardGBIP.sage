
class StdGBIP:   

    @staticmethod
    def SolveOptimizationProblem(A,optimizationOrder,feasSol, debugMode=False):
        
        def TransformVectorsIntoPolynomials(vecs,orderr='lex'): #function for transforming vectors to Algorithm1 polynomials
            st=''
            variables=[]

            #Adding t>x
            for i in range(0,len(vecs[0])+1):
                st=st+'t'+str(i)+','
                variables.append('t'+str(i))

            #Adding x<t
            for i in range(0,len(vecs)-1):
                st=st+'x'+str(i+1)+','
                variables.append('x'+str(i+1))
            st=st+'x'+str(len(vecs))
            variables.append('x'+str(len(vecs)))

            #Polynomial Ring Construction 
            PR=PolynomialRing(QQ,st,order=orderr)
            pols=[]

            #Transformation from columns to Alg1 polynomials
            for i in range(0,len(vecs)):
                pol=''#result polynomial
                inds=set(range(0,len(vecs[i])))# unused indices
                first=False#is the variable the first in the monomial

                #generating minus part
                for j in range(0,len(vecs[i])):
                    if(vecs[i][j]<0):
                        if(not(first)):
                            first=True
                        else:
                            pol=pol+'*'
                        inds.remove(j)
                        pol=pol+variables[j+1]#+1 because there is still t0

                        if(vecs[i][j]<-1):
                            pol=pol+'^'+str(-vecs[i][j])

                if(first):       #at least one negative is read
                    pol=pol+'*'
                pol=pol+variables[len(vecs[0])+i+1]+'-'

                totalZero=True#if no positive entries
                first=False

                #generating plus part from unused indices
                for j in inds:
                    if(vecs[i][j]!=0):
                        if(not(first)):
                            first=True
                        else:
                            pol=pol+'*'
                        totalZero=False
                        pol=pol+variables[j+1]#+1 because there is still t0
                        if(vecs[i][j]>1):
                            pol=pol+'^'+str(vecs[i][j])

                if(totalZero):
                    pol=pol+'1'#if no positive entries

                pols.append(pol)#add to result set


            return [pols,PR]

    ###############################
    
        [pols,PR]=TransformVectorsIntoPolynomials([A.column(i) for i in range(0,A.ncols())],optimizationOrder)
        #adding t0 polynomial
        pol=PR.gens()[0]
        for i in range(1,A.nrows()+1):
            pol=pol*PR.gens()[i]
        pol=pol-1

        pols.append(pol)
        if(debugMode):
            print('******************The polynomial ring:')
            print(PR)    
            print('******************Generators of I_t:')
            for pol in pols:    
                print(pol)
            print('************************')

        #TermOrder('wdeglex',(1,1,1,1,
        #                     4,3,2,1,
        #                     7,5,3,1,
        #                    10,7,4,1)
        #pols

        I=PR.ideal(pols)

        #I
        gB=I.groebner_basis()
        

        print("Size of the Grobner basis of I_t: "+str(len(gB)))
        
        if(debugMode):
            print("******************Valuable Groebner basis elements:")
            
        gB1=[]
        xVars=PR.gens()[(A.nrows()+1):]
        for g in gB:
            if(set(g.variables()) & set(xVars) == set(g.variables())):
                gB1.append(g)
                if(debugMode):
                    print(g)
                        
        if(debugMode):
            print("******************")
        print("Size of the Valuable Grobner basis of I_t: "+str(len(gB1)))

        polyn=1
        
        if(debugMode):
            print(len(xVars),len(feasSol))
            
        for i in range(0,len(xVars)):
            polyn=polyn*xVars[i]^feasSol[i]

        print("Feasible Solution:"+str(feasSol))
        polynn=polyn.reduce(gB1)
        print("Optimal Solution:")
        print(polynn)

        return(str(polynn))
