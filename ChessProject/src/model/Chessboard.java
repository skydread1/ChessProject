package model;

import java.util.List;

/**
 * @author loic and lucas
 * <p>
 * Chess board
 */
public class Chessboard extends java.lang.Object implements BoardGames {

	private Game game_WHITE;
	private Game game_BLACK;
	private Game game_current;
	private Game game_non_current;
	private String message = "error";

	/**
	 * No-arguments constructor
	 */
	public Chessboard() {
		this.game_WHITE = new Game(Color.WHITE);
		this.game_BLACK = new Game(Color.BLACK);
		this.game_current = this.game_WHITE;
		this.game_non_current = this.game_BLACK;
	}

	/**
	 * @return a list of all the chess HMI pieces (current and non-current)
	 */
	public java.util.List<PieceHMI> getPiecesHMI() {
		List<PieceHMI> ret = null;
		List<PieceHMI> pieces = this.game_current.getPiecesIHM();
		if (pieces.addAll(this.game_non_current.getPiecesIHM())) {
			ret = pieces;
		} else {
			setMessage("KO: non-current pieces missing");
		}
		return ret;
	}

	/**
	 * Switch player
	 */
	public void switchJoueur() {
		Game temp;
		temp = this.game_current;
		this.game_current = this.game_non_current;
		this.game_non_current = temp;
	}

	/**
	 * Check if there is no piece between the given initial and final coordinates
	 * 
	 * @param xInit
	 * @param yInit
	 * @param xFinal
	 * @param yFinal
	 * @return true if there is a piece in between
	 */
	// TODO : optimize and make clearer
	public boolean isPieceInBetween(int xInit, int yInit, int xFinal, int yFinal) {
		boolean ret = false;
		boolean reste = true;
		// si la pièce qui va se dépacer est un bishop ou une queen(déplacement
		// diagonal), on regarde si elle va ckingser une autre pièce lors de son
		// déplacement
		if (this.game_current.getPieceType(xInit, yInit).equals("Bishop")
				|| this.game_current.getPieceType(xInit, yInit).equals("Queen")) {
			if (xInit != xFinal && yInit != yFinal) {// on verifie que le déplacement est bien celui d'un bishop (pour
														// la queen)
				// on va regarder s'il y a une pièce sur chaque case qui sépare le point de
				// départ et le point d'arrivée
				for (int i = 1; i < (Math.abs(xFinal - xInit)) && reste; i++) { // si le bishop va de 1,3 a 3,5 on aura
																				// i<2+1 avec i=1 au début: le bishop se
																				// déplacant bien de 2 cases on entre 2
																				// fois dans la boucle
					if (xFinal - xInit > 0) {// si le déplacement est positif selon les x
						if (yFinal - yInit > 0) {// si le déplacement est positif selon les y
							if (this.game_WHITE.isPieceHere(xInit + i, yInit + i)
									|| this.game_BLACK.isPieceHere(xInit + i, yInit + i)) {
								ret = true;
								reste = false;
							}
						} else {// si le déplacement est negatif selon les y
							if (this.game_WHITE.isPieceHere(xInit + i, yInit - i)
									|| this.game_BLACK.isPieceHere(xInit + i, yInit - i)) {
								ret = true;
								reste = false;
							}
						}
					}

					else { // si le déplacement est negatif selon les x
						if (yFinal - yInit > 0) {// si le déplacement est positif selon les y
							if (this.game_WHITE.isPieceHere(xInit - i, yInit + i)
									|| this.game_BLACK.isPieceHere(xInit - i, yInit + i)) {
								ret = true;
								reste = false;
							}
						} else {// si le déplacement est negatif selon les y
							if (this.game_WHITE.isPieceHere(xInit - i, yInit - i)
									|| this.game_BLACK.isPieceHere(xInit - i, yInit - i)) {
								ret = true;
								reste = false;
							}
						}

					}
				}
			}
		}
		// si la pièce qui va se dépacer est une rock ou une queen(déplacement en ligne
		// dkingte), on regarde si elle va ckingser une autre pièce lors de son
		// déplacement
		if (this.game_current.getPieceType(xInit, yInit).equals("Rock")
				|| this.game_current.getPieceType(xInit, yInit).equals("Queen")) {
			if ((xFinal == xInit || yFinal == yInit)) {// on verifie que le déplacement est bien celui d'une rock (pour
														// la queen)
				if (yFinal == yInit) { // si le déplacement est horizontal
					// on va regarder s'il y a une pièce sur chaque case qui sépare le point de
					// départ et le point d'arrivée
					for (int i = 1; i < (Math.abs((xFinal - xInit))) && reste; i++) { // si la rock va de 1,3 a 3,3 on
																						// aura i<2+1 avec i=1 au début:
																						// la rock se déplacant bien de
																						// 2 cases on entre 2 fois dans
																						// la boucle
						if ((xFinal - xInit) > 0) {// si le déplacement est positif
							if (this.game_WHITE.isPieceHere(xInit + i, yInit)
									|| this.game_BLACK.isPieceHere(xInit + i, yInit)) {
								ret = true;
								reste = false;
							}
						} else {// si le déplacement est négatif
							if (this.game_WHITE.isPieceHere(xInit - i, yInit)
									|| this.game_BLACK.isPieceHere(xInit - i, yInit)) {
								ret = true;
								reste = false;
							}
						}
					}
				}

				else {// si le déplacement est vertical
						// on va regarder s'il y a une pièce sur chaque case qui sépare le point de
						// départ et le point d'arrivée
					for (int i = 1; i < (Math.abs((yFinal - yInit))) && reste; i++) { // si la rock va de 1,3 a 1,5 on
																						// aura i<2+1 avec i=1 au début:
																						// la rock se déplacant bien de
																						// 2 cases on entre 2 fois dans
																						// la boucle
						if ((yFinal - yInit) > 0) {// si le déplacement est positif
							if (this.game_WHITE.isPieceHere(xInit, yInit + i)
									|| this.game_BLACK.isPieceHere(xInit, yInit + i)) {
								ret = true;
								reste = false;
							}
						} else {// si le déplacement est négatif
							if (this.game_WHITE.isPieceHere(xInit, yInit - i)
									|| this.game_BLACK.isPieceHere(xInit, yInit - i)) {
								ret = true;
								reste = false;
							}
						}
					}
				}
			}
		}
		return ret;
	}

	/**
	 * @param xInit
	 * @param yInit
	 * @param xFinal
	 * @param yFinal
	 * @return true if the move is valid
	 */
	public boolean isMoveOk(int xInit, int yInit, int xFinal, int yFinal) {
		boolean ret = false;

		if (this.getColorCurrentPlayer().equals(this.getPieceColor(xInit, yInit))) {
			if (!(xInit == xFinal && yInit == yFinal)) {
				// capture case
				if (this.game_non_current.isPieceHere(xFinal, yFinal)) {
					ret = game_non_current.isCapturePossible(xFinal, yFinal)
							&& game_current.isMoveOk(xInit, yInit, xFinal, yFinal);
					// pawn capture case
					if (this.game_current.getPieceType(xInit, yInit).equals("Pawn")) {
						if (this.game_non_current.isCapturePossible(xFinal, yFinal)) {
							ret = ((Pawn) this.game_current.findPiece(xInit, yInit)).isMoveDiagOk(xFinal, yFinal);
						}
					}
				} else {
					// cannot capture same color piece
					if (this.game_current.isPieceHere(xFinal, yFinal)) {
						ret = false;
					
					}
					// Castling or en passant cases
					else if (this.game_current.isCastlingPossible(xInit, yInit, xFinal, yFinal)
							|| this.isEnPassantPossible(xInit, yInit, xFinal, yFinal)) {
						ret = true;
					}
					// classic move
					else {
						ret = game_current.isMoveOk(xInit, yInit, xFinal, yFinal);
					}
				}
				// piece in between case
				if (isPieceInBetween(xInit, yInit, xFinal, yFinal)) {
					ret = false;
					setMessage("KO: you shall not pass ! There is a piece on your way");
				}
			} else {
				setMessage("KO: invalid move or no move");
				ret = false;
			}
		} else {
			setMessage("KO: this piece does not belong to you thief !");
		}
		return ret;
	}

	public boolean move(int xInit, int yInit, int xFinal, int yFinal) {
		boolean ret = false;

		if (this.isMoveOk(xInit, yInit, xFinal, yFinal)) {
			// capture case
			this.game_non_current.capture(xFinal, yFinal);
			
			// castling move
			if (this.game_current.isCastlingPossible(xInit, yInit, xFinal, yFinal)) {
				ret = this.game_current.castle(xInit, yInit, xFinal, yFinal);
			}
			// en passant (move + capture)
			else if (this.isEnPassantPossible(xInit, yInit, xFinal, yFinal)) {
				ret = this.enPassant(xInit, yInit, xFinal, yFinal);
			}
			// classic move
			else {
				ret = (this.game_current.move(xInit, yInit, xFinal, yFinal));
			}
		} else {
			setMessage("KO: Move not valid");
		}
		return ret;
	}
	
	/**
	 * Check if the en passant move is possible
	 * 
	 * @see <a href="https://www.youtube.com/watch?v=1q7lZilVy04&ab_channel=Chess.com">En Passant</a>
	 * @param xInit
	 * @param yInit
	 * @param xFinal
	 * @param yFinal
	 * @return true if the en passant move is possible
	 * 
	 * TODO: the en passant move must be done right after the opponent pawn did a 2 step forward move
	 */
	public boolean isEnPassantPossible(int xInit, int yInit, int xFinal, int yFinal) {
		boolean ret = false;
		Color pawnColor = game_current.getColor();
		// white pawn
		if (pawnColor == Color.WHITE) {
			// at good y position
			if (yInit == 3 && yFinal == 2
					&& Math.abs(xFinal - xInit) == 1
					&& game_non_current.isPieceHere(xFinal, yInit)
					&& game_non_current.findPiece(xFinal, yInit).getColor() == Color.BLACK
					&& game_non_current.findPiece(xFinal, yInit).getName().equals("Pawn")) {
				ret = true;	
			}
		}
		// black pawn
		if (pawnColor == Color.BLACK) {
			// at good y position
			if (yInit == 4 && yFinal == 5
					&& Math.abs(xFinal - xInit) == 1
					&& game_non_current.isPieceHere(xFinal, yInit)
					&& game_non_current.findPiece(xFinal, yInit).getColor() == Color.WHITE
					&& game_non_current.findPiece(xFinal, yInit).getName().equals("Pawn")) {
				ret = true;	
			}
		}
		return ret;
	}
	
	/**
	 * Do the en passant move (involving capture)
	 * <p>
	 * @param xInit of the current pawn
	 * @param yInit of the current pawn
	 * @param xFinal of the current pawn
	 * @param yFinal of the current pawn
	 * @return true once the en passant move and capture are done
	 */
	public boolean enPassant(int xInit, int yInit, int xFinal, int yFinal) {
		boolean ret =false;
		if (this.isEnPassantPossible(xInit, yInit, xFinal, yFinal)) {
			// capture
			this.game_non_current.capture(xFinal, yInit);
			// move
			this.game_current.move(xInit, yInit, xFinal, yFinal);
			ret = true;
		}
		return ret;
	}

	public String toString() {
		return "Game White: \r" + this.game_WHITE.toString() + " \r Game Black: \r" + this.game_BLACK.toString() + "\r";
	}

	public Color getColorCurrentPlayer() {
		return this.game_current.getColor();
	}

	public Color getPieceColor(int x, int y) {
		return this.game_current.getPieceColor(x, y);
	}

	public java.lang.String getMessage() {
		return this.message;

	}

	private void setMessage(String message) {
		this.message = message;
	}

	public boolean isEnd() {
		return false;
		/*
		 * TODO
		 */
	}
	
	/**
	 * Unit tests
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Unit test for en passant check and move - all prints should return true");

		Chessboard chess = new Chessboard();
		System.out.println(chess.isEnPassantPossible(0, 3, 1, 2) == false);
		chess.game_current.move(0, 6, 0, 4); // move white pawn 2 steps up
		chess.game_current.move(0, 4, 0, 3); // move white pawn 1 step up
		chess.game_non_current.move(1, 1, 1, 3); // move black pawn 2 steps up
		System.out.println(chess.isEnPassantPossible(0, 3, 1, 2));
		System.out.println(chess.enPassant(0, 3, 1, 2));
		System.out.println(chess.game_current.isPieceHere(1, 3) == false); // the black pawn was captured
		System.out.println(chess.game_current.findPiece(1, 2).getName().equals("Pawn")); // the white pawn moved en passant
		
		chess.switchJoueur();
		System.out.println(chess.isEnPassantPossible(7, 4, 6, 5) == false);
		chess.game_current.move(7, 1, 7, 3); // move black pawn 2 steps up
		chess.game_current.move(7, 3, 7, 4); // move black pawn 1 step up
		chess.game_non_current.move(6, 6, 6, 4); // move white pawn 2 steps up
		System.out.println(chess.isEnPassantPossible(7, 4, 6, 5));
		System.out.println(chess.enPassant(7, 4, 6, 5));
		System.out.println(chess.game_current.isPieceHere(6, 4) == false); // the white pawn was captured
		System.out.println(chess.game_current.findPiece(6, 5).getName().equals("Pawn")); // the black pawn moved en passant
	}
}
