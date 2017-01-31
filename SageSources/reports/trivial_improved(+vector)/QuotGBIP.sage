
class QuotGBIP:   

    @staticmethod
    def SolveOptimizationProblem(A,optimizationOrder,feasSol, debugMode=False):

        def TransformVectorsIntoPolynomials(vecs,orderr='lex'): #function for transforming vectors to J_c polynomials
            st=''
            variables=[]

            #Adding t>x
            st=st + 't,'
            variables.append('t')

            #Adding x<t
            for i in range(0,len(vecs[0])-1):
                st=st+'x'+str(i+1)+','
                variables.append('x'+str(i+1))

            st=st+'x'+str(len(vecs[0]))  
            variables.append('x'+str(len(vecs[0])))
            print(st)

            #Polynomial Ring Construction 
            PR=PolynomialRing(QQ,st,order=TermOrder('lex',1)+orderr)
            pols=[]

            #Transformation from columns to Alg1 polynomials
            for i in range(0,len(vecs)):
                pol='-'#result polynomial
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
                        pol=pol+variables[j+1]#+1 because there is still t

                        if(vecs[i][j]<-1):
                            pol=pol+'^'+str(-vecs[i][j])

                if(not first):       #no negatives read
                    pol=pol+'1'
                pol=pol+'+'

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


        A=A.transpose()

        H, U =A.hermite_form(transformation=true)#HNF solution
        #H=H.transpose() #we need only the rank of it so it does not matter
        U=U.transpose()

        #A=A.transpose() #???

        AA=block_matrix([[zero_matrix(H.rank(),A.nrows()-H.rank())],[identity_matrix(A.nrows()-H.rank())]])

        rr=U*AA
        rr=rr.transpose()
        rr=rr.LLL()#to simplify basis somehow
        #print(rr)

        [pols,PR]=TransformVectorsIntoPolynomials([rr.row(i) for i in range(0,rr.nrows())],optimizationOrder)

        #adding t polynomial
        pol=PR.gens()[0]
        for i in range(1,rr.ncols()+1):
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


        I=PR.ideal(pols)
        #I
        gB=I.groebner_basis()

        
        print("Size of the Grobner basis of I_t: "+str(len(gB)))

        if(debugMode):
            print("******************Valuable Groebner basis elements:")
            
        gB1=[]
        xVars=PR.gens()[1:]
        for g in gB:
            if(set(g.variables()) & set(xVars) == set(g.variables())):
                gB1.append(g)
                if(debugMode):
                    print(g)
                    
        print("Size of the Valuable Grobner basis of I_t: "+str(len(gB1)))            
        if(debugMode):
            print("******************")


        polyn=1
        
        for i in range(0,len(xVars)):
            polyn=polyn*xVars[i]^feasSol[i]

        print("Feasible Solution:"+str(feasSol))
        polynn=polyn.reduce(gB1)
        print("Optimal Solution:")
        print(polynn)

        return(str(polynn))
